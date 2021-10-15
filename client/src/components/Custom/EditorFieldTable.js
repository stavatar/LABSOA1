import React, { Component } from 'react';
import DatePicker from 'react-datepicker';

const typeMood = [ 'SORROW', 'GLOOM', 'FRENZY'];
const typeWeapon= [ 'HAMMER', 'SHOTGUN', 'KNIFE','MACHINE_GUN', 'BAT'];


export function customMoodField(column, attr, editorClass, ignoreEditable) {
    return (
        <select className={ `${editorClass}` } { ...attr }>
            {
                typeMood.map(name => (<option key={ name } value={ name }>{ name }</option>))
            }
        </select>

    );
}
export function customWeaponField(column, attr, editorClass, ignoreEditable) {
    return (
        <select className={ `${editorClass}` } { ...attr }>
            {
                typeWeapon.map(name => (<option key={ name } value={ name }>{ name }</option>))
            }
        </select>
    );
}

export function customDateField(column, attr, editorClass, ignoreEditable) {
    return (
        <DatePicker selected={attr} dateFormat="MM/DD/YYYY" readOnly
                     className='datePickerControl'
                    showYearDropdown dropdownMode="select"/>

    );
}
export class SalesRadioField extends React.Component {

    getFieldValue = () => {
        return this.refs.yes.checked ? '0' : '1';
    }

    render() {

        return (
            <div>
                <label className='radio-inline'>
                    <input ref='yes' type='radio' name={this.props.col["field"]} value='Yes'/>Yes
                </label>
                <label className='radio-inline'>
                    <input ref='no' type='radio' name={this.props.col["field"]} value='No'/>No
                </label>
            </div>
        );
    }
}


export function customSaleField (column, attr, editorClass, ignoreEditable, defaultValue)  {
    debugger;
    return (

        <SalesRadioField ref={ attr.ref } col={column} editorClass={ editorClass } ignoreEditable={ ignoreEditable }/>
    );
}