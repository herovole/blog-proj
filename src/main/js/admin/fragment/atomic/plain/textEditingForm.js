import React from 'react';

export class TextEditingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.isFixed : forbidden to edit
            text : this.props.children ? this.props.children : "",
            fixedText : this.props.children ? this.props.children : "",
            isHidden : this.props.isHidden ? true : false,
            isBeingEdited : false,
        };
    }

    componentDidMount() { }

    switchMode = () => {
        this.setState(prevState => ({
            isBeingEdited: !prevState.isBeingEdited && !this.props.isFixed
        }));
    }

    handleChange = (e) => {
        this.setState(prevState => ({
            text: e.target.value
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedText: this.state.text,
            isBeingEdited: false
        }));
    }

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <textarea
                      class="editable-text-activated scale-large-flexible"
                      onChange={this.handleChange}
                      placeholder="Type here..."
                    >
                        {this.state.text}
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
                        {this.state.fixedText ? this.state.fixedText : "(No Text)"}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={this.state.fixedText} />
                </div>
            );
        }
    }
}

