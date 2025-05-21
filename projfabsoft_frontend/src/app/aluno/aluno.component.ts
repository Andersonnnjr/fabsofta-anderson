import { Component } from '@angular/core';
import {Aluno} from '../model/aluno';
import {ClientService} from '../service/aluno.service';

@Component({
  selector: 'app-aluno',
  imports: [],
  templateUrl: './aluno.component.html',
  styleUrl: './aluno.component.css'
})
export class AlunoComponent {

  public listaAlunos:Aluno[] = [];
  constructor(
    private alunoService:AlunoService
  ){}
  ngOnit(): void{
    this.alunoService.getAlunos().subscribe(resposta => {
      this.listaAlunos = resposta;
    })
  }

}
