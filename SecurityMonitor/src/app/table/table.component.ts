import { Component, OnInit } from '@angular/core';
import { AlertsService } from '../services/alerts.service';
import { IntervalObservable } from 'rxjs/observable/IntervalObservable';


declare var $:any;

declare interface TableData {
    headerRow: string[];
    dataRows: string[][];
}

@Component({
    selector: 'table-cmp',
    moduleId: module.id,
    templateUrl: 'table.component.html',
    providers:[AlertsService]
})

export class TableComponent implements OnInit{
    public tableData1: TableData;
    public tableData2: TableData;
    public alerts;
    alertsSize = 0;
    
    
  getAlerts() {
    this.alertsService.getAlerts().subscribe(data =>{
        this.alerts = data;
        this.alertsSize = this.alerts.lenght;
    });
  }

  showNotification(from, align){
    var type = ['','info','success','warning','danger'];

    var color = Math.floor((Math.random() * 4) + 1);

    $.notify({
        icon: "ti-gift",
        message: "Se ha generado una alerta!!."
    },{
        type:'warning',
        timer: 4000,
        placement: {
            from: from,
            align: align
        }
    });
}

    ngOnInit(){       
        this.getAlerts();
        IntervalObservable.create(1000).subscribe(() => this.getAlerts());
        this.tableData1 = {
            headerRow: [ 'ID', 'Type', 'Date', 'Latitud', 'Longitud'],
            dataRows: []
        };
        this.showNotification('top','center')
    }

    
  
  constructor(
    private alertsService: AlertsService
  ) { }

}
