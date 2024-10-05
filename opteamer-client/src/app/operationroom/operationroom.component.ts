import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PatientSevice} from "../services/patient.sevice";
import {OperationRoomSevice} from "../services/operationroom.sevice";

@Component({
  selector: 'app-operationroom',
  templateUrl: './operationroom.component.html',
  styleUrl: '../app.component.css'
})
export class OperationroomComponent implements OnInit {

  operationRoomForm!: FormGroup;
  editedOperationRoom:any;
  modalTitle!: string;
  operationRooms$: any;

  constructor(private operationRoomSevice: OperationRoomSevice) {
    this.operationRooms$ = this.operationRoomSevice.data$;
  }

  ngOnInit() {
    this.reloadOperationRooms();
    this.operationRoomForm = new FormGroup<any>({
      'roomNr': new FormControl(null, [Validators.required]),
      'buildingBlock': new FormControl(null, [Validators.required]),
      'floor': new FormControl(null, [Validators.required]),
      'type': new FormControl(null, [Validators.required]),
      'state': new FormControl(null, [Validators.required]),
    });
  }

  reloadOperationRooms() {
    this.operationRoomSevice.refreshData();
  }

  openModal(operationRoom: any) {

    this.editedOperationRoom = operationRoom;

    let roomNr = '';
    let buildingBlock = '';
    let floor = '';
    let type = '';
    let state = '';

    this.modalTitle = 'create';

    if (operationRoom) {
      roomNr = operationRoom.roomNr;
      buildingBlock = operationRoom.buildingBlock;
      floor = operationRoom.floor;
      type = operationRoom.type;
      state = operationRoom.state;
      this.modalTitle = 'edit';
    }
    this.operationRoomForm.patchValue({
      'roomNr': roomNr,
      'buildingBlock': buildingBlock,
      'floor': floor,
      'type': type,
      'state': state
    })

  }

  onSubmit() {

    let bodyObj = {
      roomNr: this.operationRoomForm.value.roomNr,
      buildingBlock: this.operationRoomForm.value.buildingBlock,
      floor: this.operationRoomForm.value.floor,
      type: this.operationRoomForm.value.type,
      state: this.operationRoomForm.value.state,
    };

    if (this.editedOperationRoom) {
      this.operationRoomSevice.putOperationRoom(this.editedOperationRoom.id, bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.operationRoomSevice.postOperationRoom(bodyObj).subscribe({
        next: this.handlePostResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }


    setTimeout(() => {
      this.reloadOperationRooms();
    }, 500);
  }

  onDeleteOperationRoom(id:string) {
    this.operationRoomSevice.deleteOperationRoom(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })
    setTimeout(() => {
      this.reloadOperationRooms();
    }, 500);
  }

  handlePostResponse(){}
  handlePutResponse(){}
  handleDeleteResponse(){}
  handleError(){}

}
