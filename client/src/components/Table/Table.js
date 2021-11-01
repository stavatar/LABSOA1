import React, { Component } from 'react';
import {BootstrapTable,
    TableHeaderColumn} from 'react-bootstrap-table';
import InsertButton from 'react-bootstrap-table/lib/toolbar/InsertButton';
import DeleteButton from 'react-bootstrap-table/lib/toolbar/DeleteButton';
import '../../css/Table.css';
import 'react-bootstrap-table/css/react-bootstrap-table.css'
import axios from "axios";
import {onAfterDeleteRow,sendEditCell} from "../../apiNet"
import {withRouter} from "react-router-dom";
import {path_server} from "../../apiNet";
import {CNav, CNavItem} from "@coreui/react";
import ReactPaginate from 'react-paginate';
import {typeMood,typeWeapon,bool_val} from "../../const";
import 'react-bootstrap-table/dist/react-bootstrap-table.min.css';
import qs from 'qs';




class Table extends Component {
    constructor(props) {
        super(props);
        axios.defaults.validateStatus = () => {
            return true;
        };
        this.handlePageClick=this.handlePageClick.bind(this);
        this.handleChangeSizePage=this.handleChangeSizePage.bind(this);
        this.handleKeyPress=this.handleKeyPress.bind(this);
        this.getAllObject=this.getAllObject.bind(this);
        this.onFilterChange=this.onFilterChange.bind(this);
        this.onSortChange=this.onSortChange.bind(this);
        this.state = {data:[],
                      sizePage:10,
                      allSizeList:1,
                      currentPage:1,
                      sort:{nameField:'id',isAscending:true},
                      filter:[]} ;
    }
    componentDidMount() {
        this.getAllObject();
        this.timer = setInterval(() => this.getAllObject(), 100000);

    }
    componentWillUnmount() {
        this.timer = null;
    }beforeClose(e) {}


    handlePageClick (data){
        debugger;
        let curPage=data.selected+1;
        this.setState({currentPage:curPage },this.getAllObject); // will call API to load data
    }
    handleChangeSizePage(event){
        debugger;
        this.setState({sizePage: event.target.value,currentPage:1 },this.getAllObject);
        event.preventDefault();
    }
    handleKeyPress(event){
        debugger;
        if (event.key === "Enter") {
            this.getAllObject();
        }
        event.preventDefault();
    }
    createCustomInsertButton = () => {
        return (
            <div className="btn-padding-left-top">
                <InsertButton
                    className="btn-sm"
                    value='Create'
                    size=""
                    onClick={ () => this.props.history.push("/add") }>Create</InsertButton>
            </div>
        );
    }
    createCustomDeleteButton = (onClick) => {
        return (
                <DeleteButton
                    btnText='Delete'
                    className="btn-sm"
                    onClick={ e => { onClick(); }}/>

     )};

    onSortChange = (sortName, sortOrder) => {
        debugger;
        let sortFlag = sortOrder==="asc";
        this.setState({sort:{nameField:sortName,isAscending:sortFlag}},this.getAllObject);
        debugger;
    }
    onFilterChange(filterObj) {
        let arr=[];
        let filterValue;
        for (const key in filterObj) {
            let current={nameField:'',action:'',value:''}
            current.nameField=key;
            switch (filterObj[key].type) {
                case 'DateFilter':{
                    debugger;
                    let filterValue = filterObj[key].value.date;
                    current.value=filterValue;
                    let comparator = filterObj[key].value.comparator;
                    current.action=comparator;
                    debugger;
                    break;
                }
                case 'NumberFilter': {
                    debugger;
                    let filterValue = filterObj[key].value.number;
                    current.value=filterValue;
                    let comparator = filterObj[key].value.comparator;
                    current.action=comparator;
                    debugger;
                    break;
                }
                default: {
                    filterValue = filterObj[key].value;
                    current.value=filterValue;
                    debugger;
                    break;
                }
            }
            arr.push(current);
            debugger;
        }
        this.setState({filter:arr},this.getAllObject);
    }
     onAfterSaveCell(row, cellName, cellValue) {
        debugger;
        sendEditCell(row, cellName, cellValue)

    }
    remote(remoteObj) {
        remoteObj.sort=true;
        remoteObj.filter=true;
        return remoteObj;
    }
    render(){
        const options = {
            hideSizePerPage: true,
            sizePerPage: 5,
            deleteBtn: this.createCustomDeleteButton,
            insertBtn: this.createCustomInsertButton,
            onSortChange: this.onSortChange,
            onFilterChange: this.onFilterChange,
            afterDeleteRow: onAfterDeleteRow,// A hook for after insert rows
        };
        const selectRowProp = {
            mode: 'checkbox',
            columnWidth: '2%',
            onSelect: this.onRowSelect,
        };
        const cellEditProp = {
            mode: 'dbclick', // 'dbclick' for trigger by double-click
            afterSaveCell: sendEditCell,
            blurToSave: true
        }

        const currentPage=Math.ceil(this.state.allSizeList/this.state.sizePage);
        debugger;
        return (
                <div>
                <BootstrapTable
                                remote
                                data={this.state.data}
                                dataField='id'
                                tdStyle={ { whiteSpace: 'normal' } }
                                insertRow
                                cellEdit={cellEditProp}
                                selectRow={ selectRowProp }
                                deleteRow={ true }
                                options={ options }
                                striped
                                hover
                                containerClass='table'
                                >
                    <TableHeaderColumn isKey
                                       dataSort
                                       dataField='id'
                                       headerAlign="left"
                                       width="2%"
                                       >
                        ID
                    </TableHeaderColumn>

                    <TableHeaderColumn dataField='name'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="4%"
                                       filter={ { type: 'TextFilter' } }
                                       >

                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='impactSpeed'
                                       dataSort
                                       filter={ { type: 'NumberFilter' } }
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"

                                       editable={ { type: 'number' } }>
                        impactSpeed
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='typeWeapon'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       filter={ { type: 'SelectFilter', options: typeWeapon} }
                                       editable={ { type: 'select', options: { values: typeWeapon } } }>
                        weaponType
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='typeMood'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       dataSort={ true }
                                       filter={ { type: 'SelectFilter', options: typeMood} }
                                       editable={ { type: 'select', options: { values: typeMood } } }>
                        moodType
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='X'
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="5%"
                                       dataSort
                                       filter={ { type: 'NumberFilter' } }
                                       editable={ { type: 'number' }} >
                        x
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='Y'
                                       dataAlign='center'
                                       dataSort
                                       headerAlign="center"
                                       width="5%"
                                       filter={ { type: 'NumberFilter' } }
                                       editable={ { type: 'number' , options: { min: '0', max: '369' } } }>
                        y
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='date'
                                       dataAlign='center'
                                       dataSort
                                       headerAlign="center"
                                       width="7%"
                                       filter={ { type: 'DateFilter' } }
                                       editable={ { type: 'date'  }}>
                        date

                    </TableHeaderColumn>

                    <TableHeaderColumn dataField='realHero'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="2%"
                                       filter={ { type: 'SelectFilter', options: bool_val} }
                                       editable={ { type: 'checkbox'  }}>
                        R
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='car'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="2%"
                                       filter={ { type: 'SelectFilter', options: bool_val} }
                                       editable={ { type: 'checkbox'  }}>
                        C
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='hasToothpick'
                                       dataSort
                                       dataAlign='center'
                                       headerAlign="center"
                                       width="2%"
                                       filter={ { type: 'SelectFilter', options: bool_val} }
                                       editable={ { type: 'checkbox'  }}>
                        H
                    </TableHeaderColumn>

                </BootstrapTable>
                    <CNav className="background-nav justify-content-lg-between">
                        <CNavItem className=" ">
                                <input  name="sizePage" type="number" onKeyPress={this.handleKeyPress}   value={this.state.sizePage} onChange={this.handleChangeSizePage} />
                        </CNavItem>
                        <CNavItem className=" ">
                            <ReactPaginate
                                previousLabel={"← Previous"}
                                nextLabel={"Next →"}
                                pageCount={currentPage}
                                onPageChange={this.handlePageClick}
                                containerClassName={"pagination"}
                                previousLinkClassName={"pagination__link"}
                                nextLinkClassName={"pagination__link"}
                                disabledClassName={"pagination__link--disabled"}
                                activeClassName={"pagination__link--active"}
                            />
                        </CNavItem>
                    </CNav>
                </div>
        );
    }

    getAllObject() {
        debugger;
        let arr_filt=JSON.stringify(this.state.filter);
        if(arr_filt==='[]')
        if(arr_filt==='[]')
            arr_filt=null;
        debugger;
        axios.get(path_server+"humanBeings", {
            params:
                {
                    sizePage: this.state.sizePage,
                    numberPage: this.state.currentPage,
                    sortField:this.state.sort.nameField,
                    isAsc:this.state.sort.isAscending,
                    filters:arr_filt
                },
            paramsSerializer: params => {
                return qs.stringify(params)
            }})
            .then(res => {
                debugger;
                const msg = res.data;
                if(msg.code ===1){
                    let k=JSON.parse(msg.data);
                    this.setState({data: k,allSizeList: msg.allSizeList});
                }
            });
    }
}

export default withRouter(Table);