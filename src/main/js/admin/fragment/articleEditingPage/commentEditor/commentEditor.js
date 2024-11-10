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
        var commentUnitList = this.props.content ? this.props.content.sort() : [];
        var elementNumber = commentUnitList.getElementNumber();

        return (
            <div>
                <div name={this.props.postKey.toStringKey()}>

                    {/* Step 2: Render each comment unit */}

                    {commentUnitList.arrayListOfComments.map((commentUnit, i) => {
                        var depth = commentUnit.depth;
                        return (
                            <div key={i} className="flex-container">
                                {[...Array(depth)].map((_, j) => (
                                    <span key={j} className="left-space" />
                                ))}
                                <CommentEditorUnit
                                  postKey={this.props.postKey.append(i.toString())}
                                  content={commentUnit}
                                />
                            </div>
                        );
                    })}

                    {/* Step 3: Render an empty CommentEditorUnit at the end */}
                    <CommentEditorUnit
                      postKey={this.props.postKey.append(elementNumber.toString())}
                      content=""
                    />
                </div>
            </div>
        );
    }
}

