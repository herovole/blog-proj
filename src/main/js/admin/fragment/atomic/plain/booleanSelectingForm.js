import React from 'react';

export class BooleanSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            check : this.props.children ? this.props.children : false,
            fixedCheck : this.props.children ? this.props.children : false,
            isBeingEdited : false
        };
    }

    componentDidMount() { }

    switchMode = () => {
        this.setState(prevState => ({
            isBeingEdited: !prevState.isBeingEdited
        }));
    }

    handleChange = (e) => {
        var isChecked = e.target.checked;
        this.setState(prevState => ({
            check: isChecked
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
                      id={this.props.postKey}
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
                <div onClick={this.switchMode} >
                    <div class="editable-text-fixed scale-minimum">
                        {this.state.fixedCheck ? "On" : "Off"}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={this.state.fixedCheck} />
                </div>
            );
        }
    }
}

