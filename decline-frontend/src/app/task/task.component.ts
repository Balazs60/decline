import { Component, OnInit } from '@angular/core';
import { TasksService } from '../services/tasks.service';
import { StatisticData, Task } from '../../types';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { FormsModule } from '@angular/forms';  // Import FormsModule
import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent {


  task: Task = {
    question: "",
    inflectedAdjective: "",
    inflectedArticle: "",
    articleAnswerOptions: [],
    adjectiveAnswerOptions: []
  }

  selectedArticle: string;
  selectedAdjective: string;
  articleValidationResult: string = '';
  adjectiveValidationResult: string = '';
  answersChecked: boolean;
  nextTaskError: string;
  isLoggedIn: boolean = false;
  isAnswerCorrect: boolean;
  statisticData: StatisticData;

  constructor(private taskService: TasksService, private loginService: LoginService) {
    this.selectedAdjective = "",
      this.selectedArticle = ""
    this.nextTaskError = "";
    this.answersChecked = false;
    this.isAnswerCorrect = false;
    this.statisticData = { isAnswerCorrect: false, unSuccessfulTask: null!, memberName: '' };
  }

  ngOnInit() {
    this.fetchTask();
    this.loginService.isLoggedIn.subscribe(isLoggedIn => {
      this.isLoggedIn = isLoggedIn;
    });
  }

  fetchTask(): void {
    this.taskService.getTask('/api/task').subscribe((task: Task) => {
      this.selectedArticle = '';
      this.selectedAdjective = '';
      this.articleValidationResult = '';
      this.adjectiveValidationResult = '';
      this.nextTaskError = "";
      this.answersChecked = false,
        console.log(task.question);
      this.task = task
    });
  }

  checkAnswersChecked(): void {
    if (this.answersChecked) {
      if (this.isLoggedIn) {
        console.log("isLoggedIn$ " + this.isLoggedIn)
        console.log("username localstorage " + localStorage.getItem('username'))
        console.log("this is from check answer check")
        this.sendStatistic()
      }
      this.fetchTask()
      
    } else {
      this.nextTaskError = "You have to check the answers!"
    }
  }

  checkAnswers(): void {
    this.answersChecked = true;
    this.articleValidationResult = this.articleAnswerValidator() ? 'Good' : 'Bad';
    this.adjectiveValidationResult = this.adjectiveAnswerValidator() ? 'Good' : 'Bad';

  }

  checkAnswerIsCorrect(){

    if(this.task.articleAnswerOptions == null){
      if(this.adjectiveAnswerValidator()){
        this.isAnswerCorrect = true
      } else {
        this.isAnswerCorrect = false
      }
    } else {
      if(this.articleAnswerValidator() && this.adjectiveAnswerValidator()){
        this.isAnswerCorrect = true
      } else {
        this.isAnswerCorrect = false;
      }
    }
  }

  articleAnswerValidator(): boolean {

    if (this.selectedArticle === this.task.inflectedArticle) {
      return true;
    } else {
      return false;
    }
  }

  adjectiveAnswerValidator(): boolean {
    if (this.selectedAdjective === this.task.inflectedAdjective) {
      return true;
    } else {
      return false;
    }
  }

  sendStatistic() {
    this.checkAnswerIsCorrect()

    this.statisticData.isAnswerCorrect = this.isAnswerCorrect
    this.statisticData.unSuccessfulTask = this.task
    this.statisticData.memberName = localStorage.getItem("username")!
    console.log("unsuccessfultask question " + this.statisticData.unSuccessfulTask.question)
    console.log("unsuccessfultask inf_adjective " + this.statisticData.unSuccessfulTask.question)
    console.log("unsuccessfultask inf_artice " + this.statisticData.unSuccessfulTask.question)

    this.taskService
      .updateStatistic(`/api/member/statistic`, this.statisticData)
      .pipe(
        catchError((error) => {
          console.error('Error sending statistic data', error);
          return of(null);
        })
      )
      .subscribe((response) => {
        if (response) {
          console.log('Statistic data sent successfully', response);
        } else {
          console.error('Failed to send statistic data');
        }
      });  }
}
