import React, { Component } from 'react';
import './App.css';
import Table1 from './components/Table1'
import PersonList from "./components/ListServerElements";
import axios from "axios";
var data = [
    {id: 1, name: 'Gob', value: '2'},
    {id: 2, name: 'Buster', value: '5'},
    {id: 3, name: 'George Michael', value: '4'}
];


class App extends Component {
    state = {
        persons: []
    }

    componentDidMount() {
        axios.get(`https://jsonplaceholder.typicode.com/users`)
            .then(res => {
                const persons = res.data;
                this.setState({ persons });
            })
    }

    render() {

        return (
            <div className="App">
                <p className="Table-header">Basic Table</p>
                <Table1 data={this.state.persons}/>
            </div>
        );
    }
}

export default App;