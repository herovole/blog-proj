import React from 'react';
import {ElementId} from '../../../domain/elementId'

export class BooleanSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : ElementId / form component name
            check : this.props.children ? this.props.children : false,
            fixedCheck : this.props.children ? this.props.children : false,
            isBeingEdited : false
        };
    }

    componentDidMount() { }


    handleChange = (e) => {
        var isChecked = e.target.checked;
        this.setState(prevState => ({
            check: isChecked
        }));
    }

    edit = () => {
        this.setState(prevState => ({
            isBeingEdited: true && !this.props.isFixed
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedCheck: this.state.check,
            isBeingEdited: false
        }));
        console.log(this.state.check + "/" + this.state.fixedCheck);
    }

    cancel = () => {
        this.setState(prevState => ({
            check: this.state.fixedCheck,
            isBeingEdited: false
        }));
        console.log(this.state.check + "/" + this.state.fixedCheck);
    }

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <input
                      type="checkbox"
                      checked={this.state.check}
                      onChange={this.handleChange}
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
                    <span class="editable-text-fixed scale-minimum">
                        {this.state.fixedCheck ? "On" : "Off"}
                    </span>
                    <input type="hidden"
                      name={this.props.postKey.toStringKey()}
                      value={this.state.fixedCheck} />
                </div>
            );
        }
    }
}

