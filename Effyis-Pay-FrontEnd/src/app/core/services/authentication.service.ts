import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {Authentication} from "../../shared/models/authentication";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<Authentication>;
  public currentUser: Observable<Authentication>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<Authentication>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): Authentication {
    return this.currentUserSubject.value;
  }

  login(login: string, password: string) {
    return this.http.post<any>(`${environment.apiUrl}/authenticate`, {login, password})
      .pipe(map(authentication => {
        localStorage.setItem('currentUser', JSON.stringify(authentication));
        this.currentUserSubject.next(authentication);
        return authentication;
      }));
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
