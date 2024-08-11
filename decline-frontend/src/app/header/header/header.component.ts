import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LoginComponent } from '../../login/login/login.component';
import { LoginService } from '../../services/login.service';
import { TasksService } from '../../services/tasks.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

   isLoggedIn: boolean = false;
   

   constructor(private router: Router,private loginService: LoginService, private taskService: TasksService) {}

   ngOnInit(): void {
    this.loginService.isLoggedIn.subscribe(loggedIn => {
      this.isLoggedIn = loggedIn;
    });
  }
  handleLogout(): void {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }

  handleLogin(): void {
    this.router.navigate(['/login']);
  }

  handleClickHomeButton(): void{
    this.taskService.unsuccessfulTask = null;
    this.router.navigate(['/'])

  }
   
}
