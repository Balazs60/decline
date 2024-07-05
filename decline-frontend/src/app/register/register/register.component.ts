import { Component } from '@angular/core';
import { RegistrationData } from '../../../types';
import { ApiService } from '../../services/api.service';
import { Observable } from 'rxjs';
import { RegistrationService } from '../../services/registration.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';  // Import this
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registrationForm: FormGroup;
  errorMessage: string = '';

  constructor( private fb: FormBuilder,
    private registrationService: RegistrationService,
    private router: Router) {
      this.registrationForm = this.fb.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
        passwordConfirm: ['', Validators.required],
       
      });
    }


  register() {
    if (this.registrationForm.valid) {
      const formValues = this.registrationForm.value;
      if (formValues.password !== formValues.passwordConfirm) {
        this.errorMessage = "Passwords do not match. Please make sure the passwords match.";
        return;
      }

      const registrationData: RegistrationData = {
        name: formValues.username,
        email: formValues.email,
        password: formValues.password
      };

    this.registrationService
      .register(`/api/v1/auth/register`, registrationData)
      .subscribe({
        next: (data) => {
          console.log("data " + data)
          if (data.token === 'fail') {
            this.errorMessage = "This username is already in use. Please try another one.";
          } else {
            localStorage.setItem('token', data.token);
            localStorage.setItem('username', formValues.username);
             this.router.navigate(['/']);
          }
        },
        error: (error) => {
          console.error(error);
          this.errorMessage = 'Registration failed. Please try again later.';
        }
      });
  }
}

}
