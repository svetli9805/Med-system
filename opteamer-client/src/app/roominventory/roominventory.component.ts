import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { RoomInventorySevice } from "../services/roominventory.sevice";
import { AssetSevice } from "../services/asset.sevice";
import { OperationRoomSevice } from "../services/operationroom.sevice";
import { combineLatest, Observable } from "rxjs";

@Component({
  selector: 'app-roominventory',
  templateUrl: './roominventory.component.html',
  styleUrl: '../app.component.css'
})
export class RoominventoryComponent implements OnInit {

  roomInventoryForm!: FormGroup;
  editedRoomInventory: any;
  modalTitle!: string;
  roomInventory$:any;
  assets$: any;
  operationRoom$: any;

  constructor(private roomInventoryService: RoomInventorySevice,
    private assetService: AssetSevice,
    private operationRoomService: OperationRoomSevice) {

    this.roomInventory$ = this.roomInventoryService.data$;
    this.assets$ = this.assetService.data$;
    this.operationRoom$ = this.operationRoomService.data$;
  }

  combined$!: Observable<[any[], any[]]>;

  ngOnInit() {
    this.reloadRoomInventories();

    this.assetService.refreshData();
    this.operationRoomService.refreshData();

    this.roomInventoryForm = new FormGroup<any>({
      'operationRoom': new FormControl(null, [Validators.required]),
      'asset': new FormControl(null, [Validators.required]),
      'count': new FormControl(null, [Validators.required])
    });

    this.combined$ = combineLatest([this.assets$, this.operationRoom$]);
  }

  reloadRoomInventories() {
    this.roomInventoryService.refreshData();
  }

  openModal(roomInventory: any) {

    this.editedRoomInventory = roomInventory;

    this.roomInventoryForm.controls['operationRoom'].enable();
    this.roomInventoryForm.controls['asset'].enable();

    let count = '';
    let assetId = '';
    let operationRoomId = '';


    this.modalTitle = 'create';

    if (roomInventory) {
      count = roomInventory.count;

      assetId = roomInventory.asset.id;
      operationRoomId = roomInventory.operationRoom.id;

      this.modalTitle = 'edit';

      //by edit existing object set the selects disabled
      this.roomInventoryForm.controls['operationRoom'].disable();
      this.roomInventoryForm.controls['asset'].disable();

    }

    this.roomInventoryForm.patchValue({
      'operationRoom': operationRoomId,
      'asset': assetId,
      'count': count
    })

  }

  onSubmit() {

    this.roomInventoryForm.controls['operationRoom'].enable();
    this.roomInventoryForm.controls['asset'].enable();

    let bodyObj = {
      assetId: this.roomInventoryForm.value.asset,
      operationRoomId: this.roomInventoryForm.value.operationRoom,
      count: this.roomInventoryForm.value.count
    };

    if (this.editedRoomInventory) {
      this.roomInventoryService.putRoomInventory(
        this.roomInventoryForm.value.asset,
        this.roomInventoryForm.value.operationRoom,
        bodyObj)
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this)
        })
    } else {
      this.roomInventoryService.postRoomInventory(bodyObj)
        .subscribe({
          next: this.handlePostResponse.bind(this),
          error: this.handleError.bind(this)
        })
    }

    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }

  onDeleteRoomInventory(assetId: string, roomId: string) {
    this.roomInventoryService.deleteRoomInventory(assetId, roomId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })
    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }


  handlePostResponse() { }
  handlePutResponse() { }
  handleDeleteResponse() { }
  handleError() { }

}
