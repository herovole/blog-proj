import React from 'react';
import axios from 'axios';
import Select from 'react-select';

/* sample code for react-select
const options = [
  { value: 'javascript', label: 'JavaScript' },
  { value: 'react', label: 'React' },
  { value: 'css', label: 'CSS' },
];

function TagSelector() {
  return (
    <Select
      isMulti
      options={options}
      placeholder="Select or type to add tags"
    />
  );
}
*/


export class TagSelectingForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            input : this.props.children ? this.props.children : "",
            fixedInput : this.props.children ? this.props.children : "",
            candidatesJapanese: [],
            candidatesEnglish: [],
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
            input: this.refTextArea.current.value,
            fixedInput: this.refTextArea.current.value,
            isBeingEdited: false
        }));
    }

    replenishOptions= () => {
        try {
            var response = await axios.post("/b/api/tags");
            this.setState(prevState => ({
                candidatesJapanese: response.data
            }));
        } catch (error) {
            console.error("error : countrySelectBox", error);
        }
    };

    render() {
        if(this.state.isBeingEdited) {
            return (
                <div>
                    <input
                      placeholder="Type here..."
                      type="text"
                      value={this.state.input}
                      onChange={this.handleInputChange}
                      ref={this.refTextArea}
                    />
                    <ul class="select-body">
                      {this.state.candidates.map((e, i) => (
                        <li class="select-element" key={i} onClick={() => this.handleSuggestionClick(e)}>
                          {e}
                        </li>
                      ))}
                    </ul>

                    <br/>
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
                    <div class="editable-text-fixed">
                        {this.state.fixedInput}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey}
                      value={this.state.fixedInput} />
                </div>
            );
        }
    }

}
