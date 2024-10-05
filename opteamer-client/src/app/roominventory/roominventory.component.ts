import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RoomInventorySevice } from '../services/roominventory.sevice';
import { AssetSevice } from '../services/asset.sevice';
import { OperationRoomSevice } from '../services/operationroom.sevice';
import { combineLatest, Observable } from 'rxjs';
import { RoomInventory } from './roominventory';
import { Asset } from './asset';
import { OperationRoom } from '../operationroom/operationroom';

@Component({
  selector: 'app-roominventory',
  templateUrl: './roominventory.component.html',
  styleUrl: '../app.component.css',
})
export class RoominventoryComponent implements OnInit {
  roomInventoryForm!: FormGroup;
  editedRoomInventory: RoomInventory | null = null;
  modalTitle!: string;
  roomInventory$: Observable<RoomInventory[]>;
  assets$: Observable<Asset[]>;
  operationRoom$: Observable<OperationRoom[]>;

  combined$!: Observable<[Asset[], OperationRoom[]]>;

  constructor(
    private roomInventoryService: RoomInventorySevice,
    private assetService: AssetSevice,
    private operationRoomService: OperationRoomSevice
  ) {
    // Използвай типизирани данни
    this.roomInventory$ = this.roomInventoryService.data$;
    this.assets$ = this.assetService.data$;
    this.operationRoom$ = this.operationRoomService.data$;
  }

  ngOnInit() {
    this.reloadRoomInventories();

    this.assetService.refreshData();
    this.operationRoomService.refreshData();

    this.roomInventoryForm = new FormGroup({
      operationRoom: new FormControl(null, [Validators.required]),
      asset: new FormControl(null, [Validators.required]),
      count: new FormControl(null, [Validators.required]),
    });

    this.combined$ = combineLatest([this.assets$, this.operationRoom$]);
  }

  reloadRoomInventories() {
    this.roomInventoryService.refreshData();
  }

  openModal(roomInventory: RoomInventory | null) {
    this.editedRoomInventory = roomInventory;

    this.roomInventoryForm.controls['operationRoom'].enable();
    this.roomInventoryForm.controls['asset'].enable();

    let count = '';
    let assetId = '';
    let operationRoomId = '';

    this.modalTitle = 'create';

    if (roomInventory) {
      count = roomInventory.count.toString();
      assetId = roomInventory.asset.id;
      operationRoomId = roomInventory.operationRoom.id;

      this.modalTitle = 'edit';

      // При редактиране, селектите се деактивират
      this.roomInventoryForm.controls['operationRoom'].disable();
      this.roomInventoryForm.controls['asset'].disable();
    }

    this.roomInventoryForm.patchValue({
      operationRoom: operationRoomId,
      asset: assetId,
      count: count,
    });
  }

  onSubmit() {
    this.roomInventoryForm.controls['operationRoom'].enable();
    this.roomInventoryForm.controls['asset'].enable();

    let bodyObj = {
      assetId: this.roomInventoryForm.value.asset,
      operationRoomId: this.roomInventoryForm.value.operationRoom,
      count: this.roomInventoryForm.value.count,
    };

    if (this.editedRoomInventory) {
      this.roomInventoryService
        .putRoomInventory(
          this.roomInventoryForm.value.asset,
          this.roomInventoryForm.value.operationRoom,
          bodyObj
        )
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this),
        });
    } else {
      this.roomInventoryService.postRoomInventory(bodyObj).subscribe({
        next: this.handlePostResponse.bind(this),
        error: this.handleError.bind(this),
      });
    }

    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }

  onDeleteRoomInventory(assetId: string, roomId: string) {
    this.roomInventoryService.deleteRoomInventory(assetId, roomId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this),
    });
    setTimeout(() => {
      this.reloadRoomInventories();
    }, 500);
  }

  handlePostResponse() {}
  handlePutResponse() {}
  handleDeleteResponse() {}
  handleError() {}
}
