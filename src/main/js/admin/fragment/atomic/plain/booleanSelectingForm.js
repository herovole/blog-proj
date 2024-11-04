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

    handleChange = (e) = {
        this.setState(prevState => ({
            check: e.target.value
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedCheck: this.state.check,
            isBeingEdited: false
        }));
    }

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <input
                      type="checkbox"
                      id={this.props.postKey}
                      checked={this.state.check}
                      onChange={handleChange}
                    />
                    </textarea>
                    <button
                      type="button"
                      onClick={this.fix}
                    >
                        Fix
                    </button>
                    <button
                      type="button"
                      onClick={this.switchMode}
                    >
                        Cancel
                    </button>
                </div>
            );
        } else {
            return (
                <div onClick={this.switchMode} >
                    <div class="editable-text-fixed scale-large">
                        {this.state.fixedCheck}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={this.state.fixedCheck} />
                </div>
            );
        }
    }
}

