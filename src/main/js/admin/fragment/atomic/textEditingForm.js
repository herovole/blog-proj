import React from 'react';
import {ElementId} from '../../../../domain/elementId'

export class TextEditingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.type : (none)... text, "id" ... id number,
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
        var currentText = e.target.value;
        this.setState(prevState => ({
            text: currentText
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
            var editorTag = "";
            if(!this.props.type) {
                editorTag = <textarea
                      class="editable-text-activated scale-large-flexible"
                      onChange={this.handleChange}
                      placeholder="Type here..."
                    >
                        {this.state.text}
                    </textarea>
            } else if(this.props.type == "id") {
                editorTag = <input
                      class="editable-text-activated scale-large-flexible"
                      type="number"
                      max="1000"
                      min="1"
                      step="1"
                      value={this.state.text}
                      checked={this.state.check}
                      onChange={this.handleChange}
                    />
            }
            return (
                <div>
                    {editorTag}
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
                      name={this.props.postKey.toStringKey()}
                      value={this.state.fixedText} />
                </div>
            );
        }
    }
}

