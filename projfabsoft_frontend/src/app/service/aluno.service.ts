import { Injectable } from '@angular/core';
import { Aluno } from '../model/aluno';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  apiURL = "http://localhost:8080/api/v1/alunos";

  constructor(private http:HttpClient) { }

  getAlunos(){
    return this.http.get<Aluno[]>(this.apiURL);
  }
}


