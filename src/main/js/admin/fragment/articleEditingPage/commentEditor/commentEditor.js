import React from 'react';
import {ElementId} from '../../../../domain/elementId'
import {CommentEditorUnit} from './commentEditorUnit'

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
            var htmlTags = [<div name={this.props.postKey.toStringKey()}></div>];
            var commentUnitList = this.props.content.sort();
            var elementNumber = 0;
            if(this.props.content) {
                elementNumber = commentUnitList.getElementNumber();
                for(var i = 0; i < elementNumber; i++) {
                    htmlTags.push(<CommentEditorUnit postKey={this.props.postKey.append(i.toString())}
                        content={commentUnitList.getCommentUnit(i)} />);
                }
            }
            htmlTags.push(<CommentEditorUnit postKey={this.props.postKey.append(elementNumber.toString())}
                content="" />);
            return htmlTags;
    }
}

