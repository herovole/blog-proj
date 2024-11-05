import React from 'react';

export class CommentEditor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnitList
        };
    }

    componentDidMount() { }

    render() {
            var htmlTags = [<div></div>];
            if(!this.props.content) { return htmlTags; }
            for(var i = 0; i < this.props.content.getElementNumber(); i++) {
                htmlTags.push(<CommentEditorUnit postKey=""
                    content={this.props.content.getCommentUnit(i)} />);
            }
            return htmlTags;
    }
}

