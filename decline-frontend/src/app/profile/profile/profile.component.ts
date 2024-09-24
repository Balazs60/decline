import { Component } from '@angular/core';
import { ProfileService } from '../../services/profile.service';
import { AnswerStatisticData } from '../../../types';
import { CommonModule } from '@angular/common';
import { TasksService } from '../../services/tasks.service';
import { Task } from '../../../types';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  answerStatisticData: AnswerStatisticData = {
    numberOfWrongAnswers: 0,
    numberOfGoodAnswers :0,
    unSuccessfulTasks: []
  }
  userName: string = "";


  constructor(private profileService: ProfileService, private taskService: TasksService, private router: Router) {
    this.userName = localStorage.getItem('username')!
  }

  ngOnInit() {
    this.fetchAnswerStatistic();
  }

  fetchAnswerStatistic(): void {
    console.log("userName " + this.userName)

    this.profileService.getAnswerStatistic(`/api/member/statistic/${this.userName}`)
    .subscribe((answerStatisticData: AnswerStatisticData) => {
      console.log("answStatData " + answerStatisticData)
     this.answerStatisticData = answerStatisticData;
    });
  }
  
  tryAgainTask(unSuccessfulTask: Task){
    this.taskService.unsuccessfulTask = unSuccessfulTask
    this.router.navigate(['task'])
  }
}
