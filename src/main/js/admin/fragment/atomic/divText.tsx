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
        <div className={className} style={{whiteSpace: "pre-wrap"}}>
            {text}
        </div>
    );
}
