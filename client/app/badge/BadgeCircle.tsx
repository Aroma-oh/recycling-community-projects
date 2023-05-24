"use client";

import Image from "next/image";
import styled from "styled-components";

interface BadgeCircleProps {
  image: { src: string; alt: string } | null;
  isAcquired: boolean;
}

const BadgeCircle: React.FC<BadgeCircleProps> = ({ image, isAcquired }) => {
  return (
    <>
      <BadgeImgContents
        style={{
          backgroundColor: isAcquired ? "#eff4e7;" : "#b5b5ba",
        }}
      >
        {image && (
          <Image
            src={image.src}
            alt={image.alt}
            width={170}
            height={170}
            style={{
              filter: isAcquired ? "none" : "grayscale(100%)",
            }}
          />
        )}
      </BadgeImgContents>
    </>
  );
};

// 중앙부 획득 뱃지 콘텐츠
const BadgeImgContents = styled.div`
  border: 1px solid #3f910c;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background-color: #eff4e7;
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 18px;
`;

export default BadgeCircle;
