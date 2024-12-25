import React from 'react';
import {UserCommentViewerUnit} from './userCommentViewerUnit'

export const UserCommentViewer = ({postKey, commentUnitList}) => {

    return (
        <div>
            {commentUnitList.arrayListOfComments.map((commentUnit, i) => {
                const depth = commentUnit.depth;
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="left-space"/>
                        ))}
                        <UserCommentViewerUnit
                            postKey={postKey.append(i.toString())}
                            content={commentUnit}
                        />
                    </div>
                );
            })}
        </div>
    );
}

