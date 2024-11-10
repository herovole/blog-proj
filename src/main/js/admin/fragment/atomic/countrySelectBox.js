import React from 'react';
import axios from 'axios';
import {ElementId} from '../../../domain/elementId'

export class CountrySelectBox extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            input : this.props.children ? this.props.children : "",
            fixedInput : this.props.children ? this.props.children : "",
            candidates: [],
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

    handleInputChange = async (event) => {
        var eventInput = event.target.value;
        if(!eventInput) {
            this.setState(prevState =>({
                input: "",
                candidates: []
            }));
            return;
        }
        try {
            var response = await axios.post("/b/api/countryselectbox", {"input" : eventInput});
            this.setState(prevState => ({
                input: eventInput,
                candidates: response.data
            }));
        } catch (error) {
            console.error("error : countrySelectBox", error);
        }
    };

    handleSuggestionClick = async (countryName) => {
        console.log("clicked " + countryName);
        try {
            var response = await axios.post("/b/api/countryselectbox", {"input" : countryName});
            this.setState(prevState => ({
               input: countryName,
               candidates: response.data
            }));
        } catch (error) {
            console.error("error : countrySelectBox", error);
        }
    }

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
                        {this.state.fixedInput ? this.state.fixedInput : "(None)"}
                    </div>
                    <input type="hidden"
                      name={this.props.postKey.toStringKey()}
                      value={this.state.fixedInput} />
                </div>
            );
        }
    }

}

