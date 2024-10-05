import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  // styleUrl: '../app.component.css'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private router: Router, private authService: AuthService) {

  }

  ngOnInit(): void {
    this.loginForm = new FormGroup<any>({
      'email': new FormControl(null, [Validators.required]),
      'password': new FormControl(null, [Validators.required])
    })
  }

  onSubmit() {
    console.log(this.loginForm.value.email)
    console.log(this.loginForm.value.password)
    this.authService.login(this.loginForm.value.email, this.loginForm.value.password)
    .subscribe(
      response => {
        console.log(response)
        this.loginForm.reset();
      },
      error => {
        console.log(error);
      }
    )
  }
}
