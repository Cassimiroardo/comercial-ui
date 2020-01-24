import { Component, OnInit } from '@angular/core';
import { OportunidadeService } from '../oportunidade.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-painel-negociacao',
  templateUrl: './painel-negociacao.component.html',
  styleUrls: ['./painel-negociacao.component.css']
})
export class PainelNegociacaoComponent implements OnInit {

  oportunidade = {}
  oportunidades = []

  constructor(
    private oportunidadeService: OportunidadeService,
    private messageService: MessageService
    ) {

     }

  ngOnInit() {
    this.consultar()
  }

  consultar() {
    this.oportunidadeService.listar()
      .subscribe(res => this.oportunidades = <any> res)
   }

   adicionar() {
    this.oportunidadeService.adicionar(this.oportunidade)
      .subscribe( _ => {
        this.oportunidade = {}
        this.consultar()

        this.messageService.add({
          severity: 'success',
          summary: 'oportunidade adicionada!'
        })
      }, res => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erro :c !'
        })
      })
   }

}
