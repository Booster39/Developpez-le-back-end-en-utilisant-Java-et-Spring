import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';
import { environment } from '../../environments/environment.prod'; 

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = `${environment.baseUrl}/user`;

  constructor(private httpClient: HttpClient) { }

  public getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }
}
