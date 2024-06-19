import { Component, OnInit } from '@angular/core';
import { TasksService } from '../services/tasks.service';
import { Task } from '../../types';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';  // Import FormsModule


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

  constructor(private taskService: TasksService) {
    this.selectedAdjective = "",
      this.selectedArticle = ""
      this.nextTaskError = "";
      this.answersChecked = false;
  }

  ngOnInit() {
    this.fetchTask();

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
}
