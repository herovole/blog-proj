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
            return (
                <CommentEditorUnit>
                </CommentEditorUnit>
            );
        }
    }
}

