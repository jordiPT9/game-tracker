import { useState } from 'react';

export const useModal = () => {
  const [isVisible, setIsVisible] = useState(false);

  const showModal = () => {
    setIsVisible(true);
  }

  const closeModal = () => {
    setIsVisible(false);
  }

  return { isVisible, showModal, closeModal };
} 