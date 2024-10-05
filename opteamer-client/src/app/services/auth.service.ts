import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, catchError, map, throwError } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private tokenKey: string = 'authToken';

    private isLoggedSubject: BehaviorSubject<boolean>;

    constructor(private httpClient: HttpClient, private router: Router) {
        const token = localStorage.getItem(this.tokenKey);
        this.isLoggedSubject = new BehaviorSubject<boolean>(!!token);
    }

    login(username: string, password: string) {
       return this.httpClient.post('http://localhost:8080/api/auth/login',
            { username: username })
            .pipe(
                map(responeseData => {
                    console.log(responeseData);
                    return responeseData;
                }),
                catchError(errorResponse => {
                    console.log(errorResponse);
                    return throwError(() => errorResponse);
                })
            )
    }
}