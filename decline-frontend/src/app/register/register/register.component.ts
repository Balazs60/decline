import { Component } from '@angular/core';
import { RegistrationData } from '../../../types';
import { RegistrationService } from '../../services/registration.service';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  isFormSubmitted: boolean = false;

  formData = {
    username: '',
    email: '',
    password: '',
    passwordConfirm: ''
  };

  errorMessage: string = '';
  isLoggedIn$: Observable<boolean>;

  constructor(
    private registrationService: RegistrationService,
    private router: Router,
    private loginService: LoginService
  ) {
    this.isLoggedIn$ = this.loginService.isLoggedIn;
  }

  register() {
    // if (this.formData.password !== this.formData.passwordConfirm) {
    //   this.errorMessage = "Passwords do not match. Please make sure the passwords match.";
    //   return;
    // }
    this.isFormSubmitted = true;
    if (!this.formData.username ||
       !this.formData.email ||
        !this.formData.password ||
         !this.formData.passwordConfirm ||
          this.formData.password !== this.formData.passwordConfirm) {
      return;
    }
    const registrationData: RegistrationData = {
      name: this.formData.username,
      email: this.formData.email,
      password: this.formData.password
    };

    this.registrationService
      .register(`/api/v1/auth/register`, registrationData)
      .subscribe({
        next: (data) => {
          if (data.token === 'fail') {
            this.errorMessage = "This username is already in use. Please try another one.";
          } else {
            this.loginService.setLoggedIn(data.token, this.formData.username);
            this.router.navigate(['/']);
          }
        },
        error: (error) => {
          console.error(error);
          this.errorMessage = 'Registration failed. Please try again later.';
        }
      });
  }

  handleLogout() {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
