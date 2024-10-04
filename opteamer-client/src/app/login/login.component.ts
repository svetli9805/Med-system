import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  // styleUrl: '../app.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.loginForm = new FormGroup<any>({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'pwd': new FormControl(null, [Validators.required])
    })
  }

  onSubmit() {

  }
}
