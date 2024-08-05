import React from "react";
import Link from "next/link";
import "./Button.css";

interface ButtonProps {
    LinkText: string;
    LinkTo: string;
}

const Button: React.FC<ButtonProps> = ({ LinkText, LinkTo }) => {
    return (
        <>
            <Link href={LinkTo} className="btn">{LinkText}</Link>
        </>
    )
}

export default Button;