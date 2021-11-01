import React, { Component } from 'react';
    import '../../css/AddForms.css';
import {withRouter} from 'react-router-dom';
import {addObj} from "../../apiNet";

import {typeMood,typeWeapon} from "../../const";

class NameForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '-',
            impactSpeed:'0',
            X: '0',
            Y:'0',
            typeMood:'SORROW',
            typeWeapon:'HAMMER',
            realHero:'true',
            car:'true',
            hasToothpick: 'true',
            date:new Date()
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        debugger;
        switch (event.target.name) {
            case "name":
                this.setState({name: event.target.value});
                break;
            case  "impactSpeed":
                this.setState({impactSpeed : event.target.value});
                break;
            case  "X":
                this.setState({X: event.target.value});
                break;
            case  "Y":
                this.setState({Y: event.target.value});
                break;
            case  "typeMood":
                this.setState({typeMood: event.target.value});
                break;
            case  "typeWeapon":
                this.setState({typeWeapon: event.target.value});
                break;
            case  "realHero":
                this.setState({realHero: event.target.checked});
                break;
            case  "hasToothpick":
                this.setState({hasToothpick: event.target.checked});
                break;
            case  "car":
                this.setState({car: event.target.checked});
                break;
        }
        event.preventDefault();
    }

    handleSubmit(event) {
        debugger;
        if(this.state.hasToothpick===false || this.state.hasToothpick===0){
            this.setState({hasToothpick: "false" });
            this.state.hasToothpick="false";
        }
        if(this.state.hasToothpick===true || this.state.hasToothpick===1){
            this.setState({hasToothpick: "true" });
            this.state.hasToothpick="true";
        }

        debugger;
        if(this.state.car===false || this.state.car===0){
            this.setState({car: "false" });
            this.state.car="false";
        }
        if(this.state.car===true ||this.state.car===1){
            this.setState({car: "true" });
            this.state.car="true";
        }

        debugger;
        if(this.state.realHero===false || this.state.realHero===0){
            this.setState({realHero: "false" });
            this.state.realHero="false";
        }
        if(this.state.realHero===true || this.state.realHero===1){
            this.setState({realHero: "true" });
            this.state.realHero="true";
        }
        debugger;
        addObj(this.state,this.props.history);

        event.preventDefault();
    }

    render() {
        return (
            <form className=' container form-control-lg ' onSubmit={this.handleSubmit}>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">Имя:</label>
                    <input  className="item2" name="name" type="text" value={this.state.name} onChange={this.handleChange} />
                </label>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">impactSpeed:</label>
                    <input className="item2" name="impactSpeed" type="number"  required value={this.state.impactSpeed} onChange={this.handleChange} />
                </label>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">X :</label>
                    <input className="item2" name="X" type="number" required value={this.state.X} onChange={this.handleChange} />
                </label>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">Y :</label>
                    <input className="item2" name="Y" type="number" required value={this.state.Y} onChange={this.handleChange} />
                </label >
                <label className="containerLabel">
                    <label className="item1 text-lg-center">typeMood :</label>
                    <select className="item2" required name="typeMood" value={this.state.typeMood} onChange={this.handleChange}>
                        {
                            typeMood.map(name => (<option key={ name } value={ name }> { name }</option>))
                        }
                </select>
                </label >
                <label className="containerLabel">
                    <label className="item1 text-lg-center">typeWeapon :</label>
                    <select className="item2" required name="typeWeapon" value={this.state.typeWeapon} onChange={this.handleChange}>
                        {
                            typeWeapon.map(name => (<option key={ name } value={ name }> { name }</option>))
                        }
                    </select>
                </label >
                <label className="containerLabel">
                    <label className="item1 text-lg-center">realHero :</label>
                    <input
                        className="checkbox"
                        name="realHero"
                        type="checkbox"
                        checked={this.state.realHero}
                        onChange={this.handleChange} />
                 </label>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">car :</label>
                    <input
                        className="checkbox"
                        name="car"
                        type="checkbox"
                        checked={this.state.car}
                        onChange={this.handleChange} />
                </label>
                <label className="containerLabel">
                    <label className="item1 text-lg-center">hasToothpick :</label>
                    <input
                        className="checkbox"
                        name="hasToothpick"
                        type="checkbox"
                        checked={this.state.hasToothpick}
                        onChange={this.handleChange} />
                </label>

                <input  type="submit" value="Отправить" />


            </form>
        );
    }
}
export default withRouter(NameForm);
