import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AssessmentSevice} from "../services/assessment.sevice";
import {PreOpAssessmentSevice} from "../services/preopassessment.sevice";
import {TeamMemberSevice} from "../services/teammember.sevice";
import {PatientSevice} from "../services/patient.sevice";
import {combineLatest, Observable} from "rxjs";

@Component({
  selector: 'app-assessment',
  templateUrl: './assessment.component.html',
  styleUrl: '../app.component.css'
})
export class AssessmentComponent implements OnInit {

  assessmentForm!: FormGroup;
  editedAssessment:any;
  modalTitle!: string;
  assessments$: Observable<any[]>;
  teamMembers$: Observable<any[]>;
  patients$: Observable<any[]>;
  preOpAssessments$: Observable<any[]>;

  constructor(private assessmentService:AssessmentSevice,
              private teamMemberService: TeamMemberSevice,
              private patientService: PatientSevice,
              private preOpAssessmentService: PreOpAssessmentSevice) {
                
  this.assessments$ = this.assessmentService.data$;
  this.teamMembers$ = this.teamMemberService.data$;
  this.patients$ = this.patientService.data$;
  this.preOpAssessments$ = this.preOpAssessmentService.data$;
  }
  combined$!: Observable<[any[], any[], any[]]>;

  ngOnInit() {
    this.reloadAssessments();
    this.teamMemberService.refreshData();
    this.patientService.refreshData();
    this.preOpAssessmentService.refreshData();

    this.assessmentForm = new FormGroup<any>({
      'teamMember': new FormControl(null, [Validators.required]),
      'patient': new FormControl(null, [Validators.required]),
      'preOperativeAssessments': new FormControl(null, [Validators.required]),
      'startdate': new FormControl(null, [Validators.required]),
    });

    this.combined$ = combineLatest([this.preOpAssessments$, this.teamMembers$, this.patients$]);
  }

  reloadAssessments() {
    this.assessmentService.refreshData();
  }

  openModal(assessment: any) {
    this.editedAssessment = assessment;

    let startDate = '';
    let teamMemberId = '';
    let patientId = '';
    let preOpAssessmentId = '';


    this.modalTitle = 'create';

    if (assessment) {
      startDate = assessment.startDate;

      teamMemberId = assessment.teamMember.id;
      patientId = assessment.patient.id;
      preOpAssessmentId = assessment.preOperativeAssessment.name;

      this.modalTitle = 'edit';
    }

    this.assessmentForm.patchValue({
      'teamMember': teamMemberId,
      'patient': patientId,
      'preOperativeAssessments': preOpAssessmentId,
      'startdate': startDate
    })

  }

  onSubmit() {
    let bodyObj = { teamMemberId: this.assessmentForm.value.teamMember,
                    preOpAssessmentId: this.assessmentForm.value.preOperativeAssessments,
                    patientId: this.assessmentForm.value.patient, startDate: this.assessmentForm.value.startdate};

    if (this.editedAssessment) {
      this.assessmentService.putAssessment(this.editedAssessment.teamMember.id,
        this.editedAssessment.patient.id, this.editedAssessment.preOperativeAssessment.name, bodyObj).subscribe({
        next: this.handlePutResponse.bind(this),
        error: this.handleError.bind(this)
      })
    } else {
      this.assessmentService.postAssessment(bodyObj).subscribe({
        next: this.handlePostResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    setTimeout(() => {
      this.reloadAssessments();
    }, 500);
  }

  onDeleteAssessment(teamMemberId:string, patientId:string, preOpAId:string) {
    this.assessmentService.deleteAssessment(teamMemberId, patientId, preOpAId).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this)
    })
    setTimeout(() => {
      this.reloadAssessments();
    }, 500);
  }


  handlePostResponse(){}
  handlePutResponse(){}
  handleDeleteResponse(){}
  handleError(){}


}
