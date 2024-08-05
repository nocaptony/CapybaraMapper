import type { Metadata } from "next";
import "./globals.css";
import Navbar from "./layout/Navbar";

export const metadata: Metadata = {
  title: "CapybaraMapper",
  description: "Find and adopt an animal now!",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        {children}</body>
    </html>
  );
}
