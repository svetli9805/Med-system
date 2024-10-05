import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {OperationReportService} from "../services/operationreport.sevice";
import {OperationSevice} from "../services/operation.sevice";
import {TeamMemberSevice} from "../services/teammember.sevice";
import {combineLatest, Observable} from "rxjs";

@Component({
  selector: 'app-operationreport',
  templateUrl: './operationreport.component.html',
  styleUrl: '../app.component.css'
})
export class OperationreportComponent implements OnInit {

  operationReportForm!: FormGroup;
  editedOperationReport:any;
  modalTitle!: string;
  operationReports$: Observable<any[]>;
  operations$: Observable<any[]>;
  teamMembers$: Observable<any[]>;

  constructor(private operationReportService:OperationReportService,
              private operationService:OperationSevice,
              private teamMemberService:TeamMemberSevice) {
                this.operationReports$ = this.operationReportService.data$;
                this.operations$ = this.operationService.data$;
                this.teamMembers$ = this.teamMemberService.data$;
  }
  combined$!: Observable<[any[], any[]]>;

  ngOnInit() {

    this.reloadOperationReports();
    this.operationService.refreshData();
    this.teamMemberService.refreshData();

    this.operationReportForm = new FormGroup<any>({
      'operation': new FormControl(null, [Validators.required]),
      'teamMember': new FormControl(null, [Validators.required]),
      'report': new FormControl(null, [Validators.required])
    });

    this.combined$ = combineLatest([this.teamMembers$, this.operations$]);
  }

  reloadOperationReports() {
    this.operationReportService.refreshData();
  }

  openModal(operationReport: any) {

    this.editedOperationReport = operationReport;

    this.operationReportForm.controls['teamMember'].enable();
    this.operationReportForm.controls['operation'].enable();

    let teamMemberId = '';
    let operationId = '';
    let report = '';


    this.modalTitle = 'create';

    if (operationReport) {
      report = operationReport.report;

      teamMemberId = operationReport.teamMember.id;
      operationId = operationReport.operation.id;

      this.modalTitle = 'edit';

      //by edit existing object set the selects disabled
      this.operationReportForm.controls['teamMember'].disable();
      this.operationReportForm.controls['operation'].disable();

    }

    this.operationReportForm.patchValue({
      'operation': operationId,
      'teamMember': teamMemberId,
      'report': report
    })

  }

  onSubmit() {

    this.operationReportForm.controls['teamMember'].enable();
    this.operationReportForm.controls['operation'].enable();

    let bodyObj = {
      teamMemberId: this.operationReportForm.value.teamMember,
      operationId: this.operationReportForm.value.operation,
      report: this.operationReportForm.value.report};

    if (this.editedOperationReport) {
      this.operationReportService.putOperationReport(
        this.operationReportForm.value.teamMember,
        this.operationReportForm.value.operation,
        bodyObj)
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this)
        })
    } else {
      this.operationReportService.postOperationReport(bodyObj)
        .subscribe({
          next: this.handlePostResponse.bind(this),
          error: this.handleError.bind(this)
        })
    }

    setTimeout(() => {
      this.reloadOperationReports();
    }, 500);
  }

  onDeleteOperationReport(teamMemberId:string, operationId:string) {
    this.operationReportService.deleteOperationReport(teamMemberId, operationId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })
    setTimeout(() => {
      this.reloadOperationReports();
    }, 500);
  }


  handlePostResponse(){}
  handlePutResponse(){}
  handleDeleteResponse(){}
  handleError(){}

}
