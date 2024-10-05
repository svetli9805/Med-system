import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { OperationProviderSevice } from "../services/operationprovider.sevice";
import { OperationTypeService } from "../services/operationtype.sevice";
import { AssetSevice } from "../services/asset.sevice";
import { PreOpAssessmentSevice } from "../services/preopassessment.sevice";
import { Observable, combineLatest } from "rxjs";

@Component({
  selector: 'app-operationtype',
  templateUrl: './operationtype.component.html',
  styleUrl: '../app.component.css'
})
export class OperationtypeComponent implements OnInit {

  operationTypeForm!: FormGroup;
  editedOperationType: any;
  modalTitle!: string;
  operationTypes$: Observable<any[]>;
  operationProviders$: Observable<any[]>;
  assets$: Observable<any[]>;
  preOpAssessments$: Observable<any[]>;

  constructor(private operationTypeService: OperationTypeService,
    private operationProviderService: OperationProviderSevice,
    private assetService: AssetSevice,
    private preOpAssessmentService: PreOpAssessmentSevice) {
    this.operationTypes$ = this.operationTypeService.data$;
    this.operationProviders$ = this.operationProviderService.data$;
    this.assets$ = this.assetService.data$;
    this.preOpAssessments$ = this.preOpAssessmentService.data$;
  }

  combined$!: Observable<[any[], any[], any[]]>;


  ngOnInit() {
    this.reloadOperationTypes();

    //init child objects
    this.operationProviderService.refreshData();
    this.assetService.refreshData();
    this.preOpAssessmentService.refreshData();

    //init form
    this.operationTypeForm = new FormGroup<any>({
      'name': new FormControl(null, [Validators.required]),
      'roomType': new FormControl(null, [Validators.required]),
      'durationHours': new FormControl(null, [Validators.required]),
      'assets': new FormControl(null, [Validators.required]),
      'operationProviders': new FormControl(null, [Validators.required]),
      'preOperativeAssessments': new FormControl(null, [Validators.required])
    });

    this.combined$ = combineLatest([this.operationProviders$, this.assets$, this.preOpAssessments$]);

  }

  reloadOperationTypes() {
    this.operationTypeService.refreshData();
  }

  openModal(operationType: any) {
    this.editedOperationType = operationType;

    let name = '';
    let roomType = '';
    let assets = '';
    let durationHours = '';
    let operationProviders = '';
    let preOperativeAssessments = '';

    this.modalTitle = 'create';

    if (operationType) {

      this.modalTitle = 'edit';

      name = operationType.name;
      roomType = operationType.roomType;
      assets = operationType.assets.map((obj: any) => obj.id);
      durationHours = operationType.durationHours;
      operationProviders = operationType.operationProviders.map((obj: any) => obj.type);
      preOperativeAssessments = operationType.preOperativeAssessments.map((obj: any) => obj.name);
    }

    this.operationTypeForm.patchValue({
      'name': name,
      'roomType': roomType,
      'durationHours': durationHours,
      'assets': assets,
      'operationProviders': operationProviders,
      'preOperativeAssessments': preOperativeAssessments,
    })

  }

  onSubmit() {

    let ops;
    this.operationProviders$.subscribe(data => {
      let ids: any[] = this.operationTypeForm.value.operationProviders;
      ops = data.filter(obj => ids.includes(obj.type))
    })

    let assets;
    this.assets$.subscribe(data => {
      let ids: any[] = this.operationTypeForm.value.assets;
      assets = data.filter(obj => ids.includes(obj.id))
    })

    let preOpAs;
    this.preOpAssessments$.subscribe(data => {
      let ids: any[] = this.operationTypeForm.value.preOperativeAssessments;
      preOpAs = data.filter(obj => ids.includes(obj.name))
    })


    let bodyObj = {
      name: this.operationTypeForm.value.name,
      roomType: this.operationTypeForm.value.roomType,
      durationHours: this.operationTypeForm.value.durationHours,
      assets: assets,
      operationProviders: ops,
      preOperativeAssessments: preOpAs
    };

    console.log(bodyObj)

    if (this.editedOperationType) {
      this.operationTypeService.putOperationType(this.editedOperationType.name, bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.operationTypeService.postOperationType(bodyObj).subscribe({
        next: this.handlePostResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadOperationTypes();
    }, 500);

  }

  onDeleteOperationType(id: string) {

    this.operationTypeService.deleteOperationType(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })

    setTimeout(() => {
      this.reloadOperationTypes();
    }, 500);
  }

  handlePostResponse() { }
  handlePutResponse() { }
  handleDeleteResponse() { }
  handleError() { }

}
