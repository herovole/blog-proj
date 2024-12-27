import React from 'react';
import {PublicUserCommentViewerUnit} from './publicUserCommentViewerUnit'

export const PublicUserCommentViewer = ({postKey, commentUnitList}) => {

    return (
        <div>
            {commentUnitList.arrayListOfComments.map((commentUnit, i) => {
                const depth = commentUnit.depth;
                return (
                    <div key={i} className="flex-container">
                        {[...Array(depth)].map((_, j) => (
                            <span key={j} className="left-space"/>
                        ))}
                        <PublicUserCommentViewerUnit
                            postKey={postKey.append(i.toString())}
                            content={commentUnit}
                        />
                    </div>
                );
            })}
        </div>
    );
}

