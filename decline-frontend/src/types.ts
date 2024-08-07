import { HttpContext, HttpHeaders, HttpParams } from "@angular/common/http";

export interface Options {
    headers?: HttpHeaders | {
        [header: string]: string | string[];
    };
    observe: 'body';
    context?: HttpContext;
    params?: HttpParams | {
        [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>;
    };
    reportProgress?: boolean;
    responseType?: 'json';
    withCredentials?: boolean;
    transferCache?: {
        includeHeaders?: string[];
    } | boolean;
}

export interface Task {
    id: number,
    question: string,
    inflectedAdjective: String,
    inflectedArticle: String,
    articleAnswerOptions: String[],
    adjectiveAnswerOptions: String[]
}

export interface LoginData {
    name: string,
    password: string,
}

export interface RegistrationData {
    name: string,
    email: string,
    password: string,
}



export interface StatisticData {
    isAnswerCorrect: boolean,
    unSuccessfulTask: Task,
    memberName: string,
}

export interface AnswerStatisticData {
    numberOfWrongAnswers: number,
    numberOfGoodAnswers: number,
    unSuccessfulTasks: Task[],
}