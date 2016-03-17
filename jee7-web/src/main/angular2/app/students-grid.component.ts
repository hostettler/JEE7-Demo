import {Component, Output, EventEmitter} from 'angular2/core';
import {AgGridNg2} from 'ag-grid-ng2/main';
import {Student} from './student';
import {StudentService} from './student.service';

import {GridOptions} from 'ag-grid/main';

@Component({
    selector: 'my-students-grid',
    directives: [AgGridNg2],
    inputs: ['onChangeCallBack'],
    styles: [
        `
        .toolbar button {margin: 2px; padding: 0px;}
    `
    ],
    templateUrl: 'app/students-grid.component.html',
    providers: [StudentService]

})
export class StudentsGridComponent {
    @Output() onSelectEmitter = new EventEmitter();
    
    constructor(private _studentService: StudentService) {
        this.gridOptions = <GridOptions>{};
        this.createColumnDefs();
        this.showGrid = true;
    }

    private gridOptions: GridOptions;
    private showGrid: boolean;
    private columnDefs: any[];
    private rowCount: string;
    private students: Student[];

    ngOnInit() {
        this._studentService.getStudents().then(students => this.students = students);
    }

    private createColumnDefs() {
        this.columnDefs = [
            {
                headerName: 'Student',
                children: [
                    {
                        headerName: "Id", field: "id",
                        width: 50, pinned: true
                    },                
                    {
                        headerName: "Firstname", field: "firstName",
                        width: 150, pinned: true
                    },
                    {
                        headerName: "Lastname", field: "lastName", width: 200,
                        pinned: true
                    },
                    {
                        headerName: "Birthdate", field: "birthDate", width: 150,
                        pinned: true
                    }
                ]
            }
        ];
    }

    private onRowClicked($event) {
        this.onSelectEmitter.next($event.node.data);
    }
}