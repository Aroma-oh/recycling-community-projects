import Header from "../Header";

export default function MemberEditLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <Header backButton={true} textContent={null} secretButton={false} />
      {children}
    </>
  );
}
