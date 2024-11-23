import React from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { format } from 'date-fns';
import {ElementId} from '../../../domain/elementId'

export class DateSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            dateFixed : this.props.dateInit ? this.props.dateInit : new Date(),
            dateEdited : this.props.dateInit ? this.props.dateInit : new Date(),
            isHidden : this.props.isHidden ? true : false,
            isBeingEdited : false
        };
    }

    componentDidMount() { }


    trackDateUpdate = (date) => {
        this.setState(prevState => ({
            dateEdited: date
        }))
    }

    edit = () => {
        this.setState(prevState => ({
            isBeingEdited: true && !this.props.isFixed
        }));
    }
    fix = () => {
        this.setState(prevState => ({
            dateFixed: this.state.dateEdited,
            isBeingEdited: false
        }));
    }
    cancel = () => {
        this.setState(prevState => ({
            dateEdited: this.state.dateFixed,
            isBeingEdited: false
        }));
    }

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <DatePicker
                      selected={this.state.dateEdited}        // The currently selected date
                      onChange={this.trackDateUpdate} // Callback when date changes
                      dateFormat="yyyy/MM/dd"      // Date format (optional)
                      placeholderText="Choose a date" // Placeholder text (optional)
                    />
                    <button
                      type="button"
                      onClick={this.fix}
                    >
                        Fix
                    </button>
                    <button
                      type="button"
                      onClick={this.cancel}
                    >
                        Cancel
                    </button>
                </div>
            );
        } else {
            return (
                <div onClick={this.edit} >
                    <div className="editable-text-fixed scale-large">
                        {format(this.state.dateFixed, 'yyyy/MM/dd')}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey.toStringKey()}
                      value={format(this.state.dateFixed, 'yyyy/MM/dd')} />
                </div>
            );
        }
    }
}
