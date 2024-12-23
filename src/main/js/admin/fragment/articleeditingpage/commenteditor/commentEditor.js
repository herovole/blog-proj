import React from 'react';
import {CommentEditorUnit} from './commentEditorUnit'

export class CommentEditor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnitList
            //this.props.countryTagOptions : TagUnitList
            countAddedComments: 0
        };
    }

    componentDidMount() {
    }

    handleAddComment = () => {
        this.setState(prevState => ({
            countAddedComments: prevState.countAddedComments + 1,
        }));
    }

    render() {
        const commentUnitList = this.props.content ? this.props.content.sort() : [];
        const elementNumber = commentUnitList.getElementNumber();

        return (
            <div>
                <div>


                    {commentUnitList.arrayListOfComments.map((commentUnit, i) => {
                        const depth = commentUnit.depth;
                        return (
                            <div key={i} className="flex-container">
                                {[...Array(depth)].map((_, j) => (
                                    <span key={j} className="left-space"/>
                                ))}
                                <CommentEditorUnit
                                    postKey={this.props.postKey.append(i.toString())}
                                    countryTagOptions={this.props.countryTagOptions}
                                    content={commentUnit}
                                />
                            </div>
                        );
                    })}

                    {Array.from({length: this.state.countAddedComments}).map((_, index) => (
                        <CommentEditorUnit
                            postKey={this.props.postKey.append((elementNumber + index).toString())}
                            countryTagOptions={this.props.countryTagOptions}
                            content=""
                        />
                    ))}

                    <p>
                        <button type="button" onClick={this.handleAddComment}>Add Comment</button>
                    </p>
                </div>
            </div>
        );
    }
}

