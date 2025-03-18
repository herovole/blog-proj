import React from 'react';

type DivTextProps = {
    children?: React.ReactNode;
    className?: string;
}


export const DivText: React.FC<DivTextProps> = ({
                                                    children = "",
                                                    className = "",
                                                }) => {
    const text = children as string;
    return (
        <div className={`comment-lf ${className}`} >
            {text}
        </div>
    );
}
