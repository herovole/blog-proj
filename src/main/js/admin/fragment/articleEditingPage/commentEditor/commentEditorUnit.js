import React from 'react';

export class CommentEditorUnit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnit
        };
    }

    componentDidMount() { }

    render() {
            return (
                <TextEditingForm postKey="id">
                    {this.props.content.commentId}
                </TextEditingForm>
                <CountrySelectBox postKey="country">
                    {this.props.content.country}
                </CountrySelectBox>
                <TextEditingForm postKey="text">
                    {this.props.content.text}
                </TextEditingForm>
                <BooleanSelectingForm postKey="isHidden">
                    {this.props.content.isHidden}
                </BooleanSelectingForm>

            );
        }
    }
}

