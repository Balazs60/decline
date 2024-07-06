import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';
import { LoginData } from '../../../types';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm = new FormGroup({
    userName: new FormControl("",Validators.required),
    password: new FormControl("",Validators.required),
  })

  errorMessage: string = '';
  isLoggedIn$: Observable<boolean>; 

  constructor(private loginService: LoginService, private router: Router) {
    this.isLoggedIn$ = this.loginService.isLoggedIn;

   }



  login() {
    const loginData: LoginData = {
      name: this.loginForm.value.userName!,
      password: this.loginForm.value.password!,
    }

    this.loginService
      .login(`/api/v1/auth/authenticate`, loginData)
      .subscribe({
        next: (data) => {
          if (data.token) {
            this.loginService.setLoggedIn(data.token,loginData.name)
            this.router.navigate(['/']);
          } else {
            this.errorMessage = 'Authentication failed. Please check your credentials.';
          }
        },
        error: (error) => {
          this.errorMessage = 'Authentication failed. Please check your credentials.';
          console.error(error);
        }      })
  }

  handleLogout() {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }

}
