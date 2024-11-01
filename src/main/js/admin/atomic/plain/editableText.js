import React from 'react';

export class EditableText extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            text : this.props.children ? this.props.children : "",
            isHidden : this.props.isHidden ? true : false,
            isBeingEdited : false
        };
        this.refTextArea = React.createRef();
    }

    componentDidMount() { }

    switchMode = () => {
        this.setState(prevState => ({
            isBeingEdited: !prevState.isBeingEdited
        }));
    }

    fix = () => {
        this.setState(prevState => ({
            text: this.refTextArea.current.value,
            isBeingEdited: false
        }));
    }

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <textarea
                      class="editable-text-activated scale-large-flexible"
                      ref={this.refTextArea}
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
                        {this.state.text}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={this.state.text} />
                </div>
            );
        }
    }
}

