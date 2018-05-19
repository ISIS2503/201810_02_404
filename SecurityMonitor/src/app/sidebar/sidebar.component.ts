import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

declare var $:any;

export interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}

export const ROUTES: RouteInfo[] = [
    { path: 'dashboard', title: 'Dashboard',  icon: 'ti-panel', class: '' },
    { path: 'user', title: 'Property Profile',  icon:'ti-user', class: '' },
    { path: 'table', title: 'Alerts List',  icon:'ti-view-list-alt', class: '' },
    { path: 'maps', title: 'Residential Unit Map',  icon:'ti-map', class: '' },        
];

@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent implements OnInit {
    public menuItems: any[];

    constructor(public auth: AuthService) {    
    }
  
    ngOnInit() {
        this.menuItems = ROUTES.filter(menuItem => menuItem);
    }
    isNotMobileMenu(){
        if($(window).width() > 991){
            return false;
        }
        return true;
    }

}
