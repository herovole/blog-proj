import React from 'react';

export class TextEditingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.isFixed : forbidden to edit
            text: this.props.children ? this.props.children : "",
            fixedText: this.props.children ? this.props.children : "",
            isHidden: this.props.isHidden,
            isBeingEdited: false,
            isLarge: false,
        };
    }

    componentDidMount() {
    }

    handleChange = (e) => {
        const currentText = e.target.value;
        this.setState(prevState => ({
            text: currentText
        }));
    }

    edit = () => {
        this.setState(prevState => ({
            isBeingEdited: !this.props.isFixed
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            fixedText: this.state.text,
            isBeingEdited: false
        }));
    }

    cancel = () => {
        this.setState(prevState => ({
            text: this.state.fixedText,
            isBeingEdited: false
        }));
    }

    render() {
        if (this.state.isBeingEdited) {

            return (
                <div>
                    <textarea
                        className={`editable-text-activated ${this.isLarge ? "scale-large-flexible" : "scale-span"}`}
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
                        onClick={this.cancel}
                    >
                        Cancel
                    </button>
                </div>
            );
        } else {
            return (
                <div onClick={this.edit}>
                    <div className={`editable-text-fixed ${this.isLarge ? "scale-large" : "scale-span"}`}>
                        {this.state.fixedText ? this.state.fixedText : "(No Text)"}
                    </div>
                    <input type="hidden"
                           name={this.props.postKey.toStringKey()}
                           value={this.state.fixedText}/>
                </div>
            );
        }
    }
}

