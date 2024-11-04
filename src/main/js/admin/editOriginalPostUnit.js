import React from 'react';
import axios from 'axios';
import { CountrySelectBox } from './atomic/countrySelectBox';
import { TextEditingForm } from './atomic/plain/textEditingForm';
import { FormEventHandler } from "react";

console.log("editOriginalPostUnit.js");
export class EditOriginalPostUnit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            text : this.props.children ? this.props.children : "",
            country : this.props.country ? this.props.country : "",
            isHidden : this.props.isHidden ? true : false,
        };
    }

    componentDidMount() { }

    handleOnSubmit = (e) => {
        e.preventDefault();

        const form = new FormData(event.currentTarget);
        const country = form.get("country") || "";
        const content = form.get("content") || "";

        console.log("country", country);
        console.log("content", content);
        this.setState(prevState => ({
            hasIntendedSubmitButtonClicked: false
        }));
    }

    render() {
        return (
            <form onSubmit={this.handleOnSubmit}>
                <CountrySelectBox postKey="country">
                    {this.state.country}
                </CountrySelectBox>
                <TextEditingForm postKey="content">
                    {this.state.text}
                </TextEditingForm>

                <p><button type="submit">Update</button></p>
            </form>
        );
    }
}

