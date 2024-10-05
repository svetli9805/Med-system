import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TeamMemberSevice } from '../services/teammember.sevice';
import { OperationProviderSevice } from '../services/operationprovider.sevice';
import { Observable } from 'rxjs';
import { TeamMember } from './teammember';
import { OperationProvider } from '../operation/operationprovider';

@Component({
  selector: 'app-teammember',
  templateUrl: './teammember.component.html',
  styleUrls: ['../app.component.css'],
})
export class TeammemberComponent implements OnInit {
  teamMemberForm!: FormGroup;
  editedTeamMember: TeamMember | null = null;
  modalTitle!: string;
  teamMembers$: Observable<TeamMember[]>;
  operationProviders$: Observable<OperationProvider[]>;

  constructor(
    private teamMemberService: TeamMemberSevice,
    private operationProviderService: OperationProviderSevice
  ) {
    this.teamMembers$ = this.teamMemberService.data$;
    this.operationProviders$ = this.operationProviderService.data$;
  }

  ngOnInit() {
    this.reloadTeamMembers();
    this.operationProviderService.refreshData();
    this.teamMemberForm = new FormGroup({
      name: new FormControl(null, [Validators.required]),
      op: new FormControl(null, [Validators.required]),
    });
  }

  reloadTeamMembers() {
    this.teamMemberService.refreshData();
  }

  openModal(teamMember: TeamMember | null) {
    this.editedTeamMember = teamMember;

    let name = '';
    let opType: string | undefined;

    this.modalTitle = 'create';

    if (teamMember) {
      name = teamMember.name;
      opType = teamMember.operationProvider.type;
      this.modalTitle = 'edit';
    }

    this.teamMemberForm.patchValue({
      name: name,
      op: opType,
    });
  }

  onSubmit() {
    if (this.editedTeamMember) {
      this.teamMemberService
        .putTeamMember(this.editedTeamMember.id, {
          name: this.teamMemberForm.value.name,
          operationProvider: { type: this.teamMemberForm.value.op },
        })
        .subscribe({
          next: this.handlePutResponse.bind(this),
          error: this.handleError.bind(this),
        });
    } else {
      this.teamMemberService
        .postTeamMember({
          name: this.teamMemberForm.value.name,
          operationProvider: { type: this.teamMemberForm.value.op },
        })
        .subscribe({
          next: this.handlePostResponse.bind(this),
          error: this.handleError.bind(this),
        });
    }

    setTimeout(() => {
      this.reloadTeamMembers();
    }, 500);
  }

  onDeleteTeamMember(id: string) {
    this.teamMemberService.deleteTeamMember(id).subscribe({
      next: this.handleDeleteResponse.bind(this),
      error: this.handleError.bind(this),
    });
    setTimeout(() => {
      this.reloadTeamMembers();
    }, 500);
  }

  handlePostResponse() {}
  handlePutResponse() {}
  handleDeleteResponse() {}
  handleError() {}
}
